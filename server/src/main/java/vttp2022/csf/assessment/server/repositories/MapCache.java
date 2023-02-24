package vttp2022.csf.assessment.server.repositories;

import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.models.ModelConversion;

import java.io.IOException;
import java.util.Optional;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.*;

public class MapCache {

	@Autowired
	AmazonS3 s3C;

	private String bucketName = "tehcsiewdai";

	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	public byte[] getMap(LatLng ll) {
		// Implmementation in here
		String key = ModelConversion.toKeyString(ll);
		Optional<byte[]> mapImage = getMapFromSpaces(key);
		if (mapImage.isPresent()) {
			return mapImage.get();
		} else {
			try (InputStream is = getMapFromChuk(ll)) {
				uploadMapToSpaces(ll, is);
			} catch (IOException ex) {
			}
			return getMap(ll);
		}
	}

	private InputStream getMapFromChuk(LatLng ll) {
		String url = UriComponentsBuilder.fromUriString("http://map.chuklee.com/map")
				.queryParam("lat", Float.toString(ll.getLatitude()))
				.queryParam("lng", Float.toString(ll.getLongitude()))
				.toUriString();
		RequestEntity req = RequestEntity.get(url).accept(MediaType.IMAGE_PNG).build();
		RestTemplate template = new RestTemplate();
		ResponseEntity<byte[]> resp = template.exchange(req, byte[].class);
		try (InputStream is = new ByteArrayInputStream(resp.getBody())) {
			return is;
		} catch (IOException ex) {
			return null;
		}
	}

	// You may add other methods to this class

	public String uploadMapToSpaces(LatLng ll, InputStream file) {
		ObjectMetadata metadata = new ObjectMetadata();

		String key = ModelConversion.toKeyString(ll);

		PutObjectRequest putReq = new PutObjectRequest(
				bucketName, // bucket name
				String.format("myobjects/%s", key), // key
				file, // inputstream
				metadata);

		putReq.withCannedAcl(CannedAccessControlList.PublicRead);

		s3C.putObject(putReq);

		return key;
	}

	private Optional<byte[]> getMapFromSpaces(String key) throws AmazonS3Exception {

		try {
			GetObjectRequest getReq = new GetObjectRequest(bucketName, key);
			S3Object result = s3C.getObject(getReq);
			try (S3ObjectInputStream is = result.getObjectContent()) {
				return Optional.of(is.readAllBytes());
			}
		} catch (AmazonS3Exception ex) {
			return Optional.empty();
		} catch (Exception ex) {
			return Optional.empty();
		}
	}

}
