package org.adorsys.adaptmt.shedules;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.adorsys.adaptmt.jpa.AptmtStatus;
import org.adorsys.adaptmt.repo.AptAptmtRepository;
import org.apache.commons.lang3.time.DateFormatUtils;

@Stateless
public class AptAptmtEventGateway {

	@Inject
	AptAptmtRepository aptAptmtRepository;

	Logger loggger = Logger.getLogger(AptAptmtEventGateway.class
			.getSimpleName());

	public void handleAptAptmtOngoingEvent(
			@Observes @AptAptmtOngoingEvent AptAptmt ptAptmt) {
		loggger.log(Level.INFO,
				" Event works perfectly -----------------   DASSI ORLEANDO");
	}

	@Schedule(minute = "*/1", hour = "*")
	public void checkAppointementDateForStatus() {

		loggger.log(Level.INFO, " check appointement date every minutes ");
		List<AptAptmt> aptaptmts = aptAptmtRepository.findAll();
		Date now = new Date();

		for (AptAptmt aptAptmt : aptaptmts) {
			String nowStrDate = DateFormatUtils.ISO_DATE_FORMAT.format(now);
			String aptStrDate = DateFormatUtils.ISO_DATE_FORMAT.format(aptAptmt
					.getAppointmentDate());

			String nowStrHour = DateFormatUtils.format(now, "HH");
			String aptStrHour = DateFormatUtils.format(
					aptAptmt.getAppointmentDate(), "HH");

			loggger.log(Level.INFO, nowStrDate + " and hour " + nowStrHour);
			loggger.log(Level.INFO, aptStrDate + " and hour " + aptStrHour);

			if ((nowStrDate.equals(aptStrDate))
					&& (nowStrHour.equals(aptStrHour))) {
				loggger.log(Level.INFO,
						" now we can change status of appointment  ONGOING");

				if (aptAptmt.getStatus().equals(AptmtStatus.FORTHCOMMING)) {
					aptAptmt.setStatus(AptmtStatus.ONGOING);
					aptAptmtRepository.save(aptAptmt);
				}

			}

			/* Integer nowH = Integer.valueOf(nowStrHour);
			Integer aptStrH = Integer.valueOf(aptStrHour);

			if ((nowStrDate.equals(aptStrDate)) && (nowH == (aptStrH + 2))) {
				loggger.log(Level.INFO,
						" now we can change status of appointment to  CLOSED");

				if (aptAptmt.getStatus().equals(AptmtStatus.ONGOING)) {
					aptAptmt.setStatus(AptmtStatus.CLOSED);
					aptAptmtRepository.save(aptAptmt);
				}

			} */

		}

	}
}
