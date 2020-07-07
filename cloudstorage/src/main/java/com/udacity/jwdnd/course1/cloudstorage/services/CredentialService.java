package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.mappers.NoteMapper;

@Service
public class CredentialService {

	private final EncryptionService encryptionService;
	private final CredentialMapper credentialMapper;

	public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
		this.encryptionService = encryptionService;
		this.credentialMapper = credentialMapper;
	}

	/**
	 * @param username
	 * @return
	 */
	public List<Credential> getCredentials(Integer userid) {
		return credentialMapper.getCredentialByUser(userid).stream().map(credential -> populateDecryptedPwd(credential))
				.collect(Collectors.toList());
	}

	private Credential populateDecryptedPwd(Credential credential) {
		credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
		return credential;
	}

	/**
	 * @param user
	 * @return
	 */
	public int addCredential(Credential credential, Integer userId) {
		encryptPassword(credential);
		return credentialMapper.insert(
				new Credential(null, credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword(), userId));
	}

	public void deleteCredential(Integer credentialId) {
		credentialMapper.deleteCredential(credentialId);
	}

	public int updateCredential(Credential credential) {
		encryptPassword(credential);
		return credentialMapper.updateCredential(credential);
	}

	private void encryptPassword(Credential credential) {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		// generating a random number and assign in salt
		random.nextBytes(key);
		String encodedkey = Base64.getEncoder().encodeToString(key);

		String encryptedPwd = encryptionService.encryptValue(credential.getPassword(), encodedkey);

		credential.setKey(encodedkey);
		credential.setPassword(encryptedPwd);
	}

}
