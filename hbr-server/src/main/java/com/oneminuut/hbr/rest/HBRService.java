package com.oneminuut.hbr.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.oneminuut.hbr.dao.domain.BED_STATUS;
import com.oneminuut.hbr.dao.domain.BedReservation;
import com.oneminuut.hbr.dao.domain.RESERVATION_STATUS_TYPE;
import com.oneminuut.hbr.dao.domain.RESERVATION__REQUEST_TYPE;
import com.oneminuut.hbr.dao.domain.User;
import com.oneminuut.hbr.dto.BedDTO;
import com.oneminuut.hbr.dto.BedReservationDTO;
import com.oneminuut.hbr.dto.DepartmentDTO;
import com.oneminuut.hbr.dto.HBRResponse;
import com.oneminuut.hbr.dto.HospitalDTO;
import com.oneminuut.hbr.dto.UnitDTO;
import com.oneminuut.hbr.dto.UserAuthenticationDTO;
import com.oneminuut.hbr.dto.UserValidationDTO;
import com.oneminuut.hbr.service.HospitalService;
import com.oneminuut.hbr.service.UserService;

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

	@POST
	@Path("/reserve-bed")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveBed(BedReservationDTO bedReservationDTO) {
		HBRResponse hbrResponse = new HBRResponse();

		UserService userService = (UserService) appContext
				.getBean("userService");
		try {

			User user = userService.getUser(bedReservationDTO.getUserId());

			if (null == user) {

				hbrResponse.setStatusCode("ERROR_007");
				hbrResponse.setMessage("User Not Found Unsuccessful");

			} else {

				HospitalService hospitalService = (HospitalService) appContext
						.getBean("hospitalService");
				// Create date using date passed in
				Date endDate = new Date();
				Date startDate = new Date();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				try {
					endDate = sdf.parse(bedReservationDTO.getEndDate());
					if (null != bedReservationDTO.getStartDate()) {
						startDate = sdf.parse(bedReservationDTO.getStartDate());
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (bedReservationDTO.getRequestType().equals(
						RESERVATION__REQUEST_TYPE.NEW_RESERVATION.toString())) {
					BedReservation bedReservation = hospitalService
							.getBedReservationForDate(
									bedReservationDTO.getBedId(), endDate,
									startDate);

					if (null != bedReservation) {
						hbrResponse.setStatusCode("ERROR_008");
						hbrResponse
								.setMessage("Reservation Unsuccessful : Bed Not Free within this date range");

					} else {
						bedReservation = new BedReservation();
						bedReservation.setCreated(new Date());
						bedReservation.setUser(user);
						bedReservation.setUpdated(new Date());
						bedReservation.setEndDate(endDate);
						bedReservation.setStartDate(startDate);
						bedReservation.setBed(hospitalService
								.getBed(bedReservationDTO.getBedId()));
						bedReservation
								.setStatus(RESERVATION_STATUS_TYPE.OCCUPIED);
						hospitalService.saveBedReservation(bedReservation);
						hbrResponse.setStatusCode("SUCCESS_008");
						hbrResponse.setMessage("Reservation Successful");
					}

				} else if (bedReservationDTO.getRequestType().equals(
						RESERVATION__REQUEST_TYPE.MARK_FREE.toString())) {
					BedReservation bedReservation = hospitalService
							.getBedReservationForDate(
									bedReservationDTO.getBedId(), endDate,
									startDate);

					if (null == bedReservation) {
						hbrResponse.setStatusCode("ERROR_009");
						hbrResponse
								.setMessage("No Reservation Present For This Date");

					} else {
						// Make an log entry
						bedReservation.setUpdated(new Date());
						bedReservation.setEndDate(endDate);
						bedReservation
								.setStatus(RESERVATION_STATUS_TYPE.MARKED_FREE);
						hospitalService.saveBedReservation(bedReservation);
						hbrResponse.setStatusCode("SUCCESS_008");
						hbrResponse.setMessage("Status Change Request Successful");
					}

				} else if (bedReservationDTO.getRequestType().equals(
						RESERVATION__REQUEST_TYPE.MARK_CLOSED.toString())) {
					BedReservation bedReservation = hospitalService
							.getBedReservationForDate(
									bedReservationDTO.getBedId(), endDate,
									startDate);

					if (null != bedReservation) {
						hbrResponse.setStatusCode("ERROR_009");
						hbrResponse
								.setMessage("Bed Already Reserved For This Date Range");

					} else {
						bedReservation = new BedReservation();
						bedReservation.setCreated(new Date());
						bedReservation.setUser(user);
						bedReservation.setUpdated(new Date());
						bedReservation.setEndDate(endDate);
						bedReservation.setStartDate(startDate);
						bedReservation.setBed(hospitalService
								.getBed(bedReservationDTO.getBedId()));
						bedReservation
								.setStatus(RESERVATION_STATUS_TYPE.CLOSED);
						hospitalService.saveBedReservation(bedReservation);
						hbrResponse.setStatusCode("SUCCESS_008");
						hbrResponse.setMessage("Reservation Successful");
					}
				} else if (bedReservationDTO.getRequestType().equals(
						RESERVATION__REQUEST_TYPE.REMOVE_CLOSED.toString())) {
					BedReservation bedReservation = hospitalService
							.getBedReservationForDate(
									bedReservationDTO.getBedId(), endDate,
									startDate);

					if (null == bedReservation) {
						hbrResponse.setStatusCode("ERROR_009");
						hbrResponse
								.setMessage("No Reservation Present For This Date");

					} else {
						// Make an log entry
						bedReservation.setEndDate(endDate);
						bedReservation.setDeleted(true);
						bedReservation.setUpdated(new Date());
						hospitalService.saveBedReservation(bedReservation);
						hbrResponse.setStatusCode("SUCCESS_008");
						hbrResponse.setMessage("Status Change Request Successful");
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
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
			@QueryParam("date") String dateString) {
		HospitalService hospitalService = (HospitalService) appContext
				.getBean("hospitalService");
		HospitalDTO hospitaldto = hospitalService.getHospital(hospitalId);

		// Create date using date passed in
		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date = sdf.parse(dateString);
			System.out.println("Date is" + date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BED_STATUS statusDate = null;

		BED_STATUS statusYesterday = null;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		Date yesterdayDate = c.getTime();

		for (DepartmentDTO departmentDTO : hospitaldto.getDepartments()) {
			for (UnitDTO unitDTO : departmentDTO.getUnits()) {
				Set<BedDTO> beds = unitDTO.getBeds();
				for (BedDTO bed : beds) {
					// TODO : Correct the logic for getting bed status
					List<BedReservation> reservations = hospitalService
							.getReservationsForBed(bed.getId(), date);

					statusDate = BED_STATUS.GREEN;
					if (null == reservations || reservations.size() == 0) {
						bed.setStatus(statusDate.getStatus());
						continue;
					}

					for (BedReservation bedReservation : reservations) {

						// Check for todays date status

						if (date.equals(bedReservation.getEndDate())) {
							statusDate = BED_STATUS.ORANGE;

							if (date.equals(bedReservation.getStartDate())) {
								statusDate = BED_STATUS.BLUE; // Today for day
																// care.
							}
							if (bedReservation.getStatus() == RESERVATION_STATUS_TYPE.CLOSED) {
								statusDate = BED_STATUS.GRAY;
							}
						}

						if (date.equals(bedReservation.getStartDate())) {
							statusDate = BED_STATUS.PINK;

							if (date.equals(bedReservation.getEndDate())) {
								statusDate = BED_STATUS.BLUE; // Today for day
																// care.
							}
							if (bedReservation.getStatus() == RESERVATION_STATUS_TYPE.CLOSED) {
								statusDate = BED_STATUS.GRAY;
							}
						}

						if (yesterdayDate.equals(bedReservation.getEndDate())) {

							if (bedReservation.getStatus() == RESERVATION_STATUS_TYPE.OCCUPIED) {
								statusYesterday = BED_STATUS.ORANGE_BLINKING; // ORANGE
																				// Blinking
							}
							/*
							 * // if not updated // already else // GREEN if
							 * (bedReservation.getStartDate().equals(
							 * yesterdayDate)) { statusDate = BED_STATUS.GREEN;
							 * // yesterday was // for // daycare show today as
							 * // green. }
							 */

						}

						if (date.after(bedReservation.getStartDate())) {
							if (date.before(bedReservation.getEndDate())) {
								statusDate = BED_STATUS.PINK;

								if (bedReservation.getStatus() == RESERVATION_STATUS_TYPE.CLOSED) {
									statusDate = BED_STATUS.GRAY;
								}
							}
							if (date.equals(bedReservation.getEndDate())) {
								statusDate = BED_STATUS.ORANGE;
								if (bedReservation.getStatus() == RESERVATION_STATUS_TYPE.CLOSED) {
									statusDate = BED_STATUS.GRAY;
								}
							}
						}

					}
					bed.setStatus(statusDate.getStatus());
					if (null != statusYesterday) {
						bed.setYesterdayStatus(statusYesterday.getStatus());
					}
				}
			}
		}

		return Response.status(200).entity(hospitaldto).build();
	}

}
