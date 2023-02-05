package com.event.organizer.api.exception;
/**Exception class for when a user is not an admin.*/
public class UserNotAdminException extends Exception{
    public UserNotAdminException(String message) {super(message);}
}
