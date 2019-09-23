package com.salesianostriana.dam.gestiapp.formbeans;

import com.salesianostriana.dam.gestiapp.model.AppUser;
import com.salesianostriana.dam.gestiapp.model.ReservedDate;
import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.model.School;
import com.salesianostriana.dam.gestiapp.model.TimeZone;

public class ReserveFormBean {

	private Room reservedRoom;
	private AppUser reserveUser;
	private ReservedDate date;
	private TimeZone timeZone;
	private School school;

	public ReserveFormBean() {
		super();
	}

	public Room getReservedRoom() {
		return reservedRoom;
	}

	public void setReservedRoom(Room reservedRoom) {
		this.reservedRoom = reservedRoom;
	}

	public AppUser getReserveUser() {
		return reserveUser;
	}

	public void setReserveUser(AppUser reserveUser) {
		this.reserveUser = reserveUser;
	}

	public ReservedDate getDate() {
		return date;
	}

	public void setDate(ReservedDate date) {
		this.date = date;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}