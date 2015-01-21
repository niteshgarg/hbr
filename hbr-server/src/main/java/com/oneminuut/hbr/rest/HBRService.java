package com.oneminuut.hbr.rest;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oneminuut.hbr.dao.domain.Bed;
import com.oneminuut.hbr.dao.domain.BedReservation;
import com.oneminuut.hbr.dao.domain.User;
import com.oneminuut.hbr.dto.BedDTO;
import com.oneminuut.hbr.dto.DepartmentDTO;
import com.oneminuut.hbr.dto.HBRResponse;
import com.oneminuut.hbr.dto.HospitalDTO;
import com.oneminuut.hbr.dto.UnitDTO;
import com.oneminuut.hbr.dto.UserAuthenticationDTO;
import com.oneminuut.hbr.dto.UserValidationDTO;
import com.oneminuut.hbr.service.HospitalService;
import com.oneminuut.hbr.service.UserService;
import com.oneminuut.hbr.util.PropertiesFileReaderUtil;

@Path("hbr-service")
public class HBRService {

	private static final Logger logger = Logger.getLogger(HBRService.class);

	ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"../applicationContext.xml");

	@Path("/text")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String text() {
		return "Hello Jersey";
	}

	@POST
	@Path("/authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(UserAuthenticationDTO userAuthDTO) {
		HBRResponse hbrResponse = new HBRResponse();

		UserService userService = (UserService) appContext
				.getBean("userService");
		try {
			User user = new User();
			user.setEmail(userAuthDTO.getEmail());

			user.setPassword(userAuthDTO.getPassword());

			UserValidationDTO userValidationDTO = userService
					.validateUser(user);
			if (null == userValidationDTO) {
				User existingUser = userService.getUserByEmail(userAuthDTO
						.getEmail());
				if (null != existingUser
						&& existingUser.getPassword().equals(
								userAuthDTO.getPassword())) {
					hbrResponse.setStatusCode("SUCCESS_003");
					hbrResponse.setMessage("User Login Successful");

				} else {
					hbrResponse.setStatusCode("ERROR_007");
					hbrResponse.setMessage("User Login Unsuccessful");
				}
			} else {
				hbrResponse.setStatusCode(userValidationDTO.getStatusCode());
				hbrResponse.setMessage(userValidationDTO.getMessage());
			}
		} catch (Exception e) {
			logger.fatal(e.getStackTrace());
			hbrResponse.setStatusCode("err001");
			hbrResponse.setMessage(e.getStackTrace().toString());
			return Response.status(500).entity(hbrResponse).build();
		}
		return Response.status(200).entity(hbrResponse).build();
	}

	@Path("/get-hospital-details")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHospitalDetails(
			@QueryParam("hospitalId") long hospitalId,
			@QueryParam("date") String date) {
		HospitalService hospitalService = (HospitalService) appContext
				.getBean("hospitalService");
		HospitalDTO hospitaldto = hospitalService.getHospital(hospitalId);

		// Create date using date passed in
		Date dateNew = new Date();

		for (DepartmentDTO departmentDTO : hospitaldto.getDepartments()) {
			for (UnitDTO unitDTO : departmentDTO.getUnits()) {
				List<Bed> beds = hospitalService
						.getBedsForUnit(unitDTO.getId());
				unitDTO.setBeds(new HashSet<BedDTO>());
				for (Bed bed : beds) {
					BedDTO bedDTO = new BedDTO();
					unitDTO.getBeds().add(bedDTO);
					bedDTO.setId(bed.getId());
					bedDTO.setNumber(bed.getNumber());
					// TODO : Correct the logic for getting bed status
					if (bed.getAvailable()) {
						Set<BedReservation> reservations = bed
								.getReservations();
						if (null == reservations || reservations.size() == 0) {
							bedDTO.setStatus("GREEN");
							continue;
						}
						for (BedReservation bedReservation : reservations) {
							if (bedReservation.getEndDate().equals(dateNew)) {
								bedDTO.setStatus("ORANGE");
								break;
							} else if (bedReservation.getStartDate().equals(
									dateNew)) {
								bedDTO.setStatus("PINK");
								break;
							}
						}
					} else {
						bedDTO.setStatus("GRAY");
					}
				}
			}
		}

		return Response.status(200).entity(hospitaldto).build();
	}

}
