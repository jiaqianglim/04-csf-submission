package vttp2022.csf.assessment.server.repositories;

import vttp2022.csf.assessment.server.models.LatLng;;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class MapCache {

	@autowired
	S3Config s3Config;

	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	public String getMap(LatLng ll) {
		// Implmementation in here

	}

	// You may add other methods to this class

	public String uploadMapToSpaces(LatLng ll, MultipartFile file) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		String key = Float.toString(ll.getLatitude()) + Float.toString(ll.getLongitude());

		PutObjectRequest putReq = new PutObjectRequest(
				"techcsiewdai", // bucket name
				String.format("myobjects/%s", key), // key
				file.getInputStream(), // inputstream
				metadata);

		putReq.withCannedAcl(CannedAccessControlList.PublicRead);

		s3Config.putObject(putReq);

		return key;
	}

}
