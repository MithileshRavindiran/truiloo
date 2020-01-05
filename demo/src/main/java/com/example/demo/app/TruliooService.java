package com.example.demo.app;

import com.trulioo.normalizedapi.ApiException;
import com.trulioo.normalizedapi.api.ConfigurationApi;
import com.trulioo.normalizedapi.api.ConnectionApi;
import com.trulioo.normalizedapi.api.VerificationsApi;
import com.trulioo.normalizedapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by rpandey3 on 4/1/20.
 */

@Service
public class TruliooService {

	private static final String SERVICE = "Identity Verification";
	
	@Autowired
	ConnectionApi connectionApi;
	
	@Autowired
	@Qualifier("truilooConfigApi")
	ConfigurationApi configurationApi;
	
	@Autowired
	VerificationsApi verificationsApi;
	
	@Autowired
	RestTemplate restTemplate;
	
	public String authenticate() throws ApiException {
		String result  =  connectionApi.testAuthentication();
		return result;
	}
	
	public List<String> getCountries() throws ApiException {
		List<String> countries = configurationApi.getCountryCodes(SERVICE);
		System.out.println(countries);
		return countries;
	}
	
	public VerifyResult verify() throws ApiException {
		VerifyRequest request = new VerifyRequest()
			    .acceptTruliooTermsAndConditions(Boolean.TRUE)
			    .demo(true)
			    .cleansedAddress(false)
			    .configurationName("Identity Verification")
			    .countryCode("AU")
			    .consentForDataSources(new ArrayList<>(Arrays.asList(
			        "Australia Driver License",
			        "Australia Passport",
			        "Birth Registry",
			        "Visa Verification",
			        "DVS Driver License Search",
			        "DVS Medicare Search",
			        "DVS Passport Search",
			        "DVS Visa Search",
			        "DVS ImmiCard Search",
			        "DVS Citizenship Certificate Search",
			        "DVS Certificate of Registration by Descent Search",
			        "Credit Agency")))
			    .dataFields(new DataFields()
			        .personInfo(new PersonInfo()
			            .firstGivenName("John")
			            .middleName("Henry")
			            .firstSurName("Smith")
			            .yearOfBirth(1983)
			            .monthOfBirth(3)
			            .dayOfBirth(5)
			            .gender("M"))
			        .location(new Location()
			            .postalCode("3108")
			            .buildingNumber("10")
			            .unitNumber("3")
			            .streetName("Lawford")
			            .streetType("st")
			            .suburb("Doncaster")
			            .stateProvinceCode("VIC")
			            .city("Vancouver"))
			        .communication(new Communication()
			            .telephone("03 9896 8785")
			            .emailAddress("testpersonAU@gdctest.com"))
			            .driverLicence(new DriverLicence()
			            .number("076310691")
			            .state("VIC")
			            .dayOfExpiry(3)
			            .monthOfExpiry(4)
			            .yearOfExpiry(2021))
			        .nationalIds(Arrays.asList(new NationalId()
			            .number("5643513953")
			            .type("Health")))
			        .passport(new Passport()
			            .mrz1("P<SAGSMITH<<JOHN<HENRY<<<<<<<<<<<<<<<<<<<<<<")
			            .mrz2("N1236548<1AUS8303052359438740809<<<<<<<<<<54")
			            .number("N1236548")
			            .dayOfExpiry(5)
			            .monthOfExpiry(12)
			            .yearOfExpiry(2018))
			        .countrySpecific(new HashMap<String, Map<String, String>>(){{
			            put("AU", new HashMap<String, String>(){{
			                put("AuImmiCardNumber", "EIS123456");
			                put("CitizenshipAcquisitionDay", "15");
			                put("CitizenshipAcquisitionMonth", "4");
			                put("CitizenshipAcquisitionYear", "1987");
			                put("CountryOfBirth", "Australia");
			                put("FamilyNameAtBirth", "Smith");
			                put("MedicareColor", "Blue");
			                put("MedicareMonthOfExpiry", "12");
			                put("MedicareReference", "2");
			                put("MedicareYearOfExpiry", "2017");
			                put("PassportCountry", "Australia");
			                put("PlaceOfBirth", "Melbourne");
			                put("RegistrationNumber", "565659");
			                put("RegistrationState", "NSW");
			                put("StockNumber", "ACD1234567");}});
			}}));
		
			//verify
			VerifyResult result = verificationsApi.verify(request);
			System.out.println(result);
		return result;
	}
	
	public String verifyUsingREST() throws ApiException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-trulioo-api-key", "cfffb9d452479019f8f5267bc5994667");
		String requestJson = "{\"AcceptTruliooTermsAndConditions\":true,\"CleansedAddress\":false,\"ConfigurationName\":\"Identity Verification\",\"ConsentForDataSources\":[\"Visa Verification\"],\"CountryCode\":\"AU\",\"DataFields\":{\"PersonInfo\":{\"DayOfBirth\":5,\"FirstGivenName\":\"J\",\"FirstSurName\":\"Smith\",\"MiddleName\":\"Henry\",\"MinimumAge\":0,\"MonthOfBirth\":3,\"YearOfBirth\":1983},\"Location\":{\"BuildingNumber\":\"10\",\"PostalCode\":\"3108\",\"StateProvinceCode\":\"VIC\",\"StreetName\":\"Lawford\",\"StreetType\":\"St\",\"Suburb\":\"Doncaster\",\"UnitNumber\":\"3\"},\"Communication\":{\"EmailAddress\":\"testpersonAU%40gdctest.com\",\"Telephone\":\"03 9896 8785\"},\"Passport\":{\"Number\":\"N1236548\"}}}\r\n"
				+ "\r\n" + "";
		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"https://gateway.trulioo.com/trial/verifications/v1/verify", HttpMethod.POST, entity, String.class);

		return response.getBody();

	}
	
	
}
