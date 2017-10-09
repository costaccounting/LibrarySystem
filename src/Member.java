/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.time.LocalDate;

/**
 *
 * @author prodip
 */
public class Member {

    private int _id; //member id as the primary key for member table
    private String _name; //member's name
    private String _address = ""; //member's address
    private LocalDate _join; //member's join date
    private LocalDate _expires; // date when membership expires
    private boolean _isExpired; //membership expired status

    Member(int id, String name, String address, String join) { //constructor for member with name, address, and join date
        this._id = id;
        this._name = (name);
        this._address = address;
        this._join = str2Date(join);
        this._expires = str2Date(join).plusYears(1);
        this._isExpired = LocalDate.now().isAfter(_expires); //condition if membership is expired
    }

    Member(int id, String name, String address, String join, String expire) { //constructor for member with name, address, and join date and expire date
        this._id = id;
        this._name = (name);
        this._address = address;
        this._join = str2Date(join);
        this._expires = str2Date(expire);
        this._isExpired = LocalDate.now().isAfter(_expires); //condition if membership is expired
    }

    Member(int id, String name) { //constructor for member with name
        this._id = id;
        this._name = (name);
        this._join = LocalDate.now(); //adding current date from system as join date
        this._expires = LocalDate.now().plusYears(1); //auto adding expiring time
        this._isExpired = false;

    }

    /**
     * method is used to renew membership by add an year to the current date and
     * set as expire date.
     */
    public void renew() {
        this._expires = LocalDate.now().plusYears(1);

    }

    public int get_id() { //getter for member id
        return this._id;
    }

    public String get_name() { //getter for member's name
        return this._name;
    }

    public String get_joinDate() { //getter for members join date as a string
        return this._join.toString();
    }

    public String get_expireDate() { //getter for membership expiracy date as a string
        return this._expires.toString();
    }

    public boolean isExpired() { //getter for expiracy status
        return _isExpired;
    }

    public String get_address() { //getter for address
        return _address;
    }

    public void set_address(String _address) { //setter for address
        this._address = _address;
    }

    public void set_name(String name) {//setter for name
        this._name = name;
    }

    public void set_expireDate(String date) { //setter for expire date, input string is converted to date format
        this._expires = str2Date(date);
    }

    public void set_joinDate(String date) {  //setter for join date, input string is converted to date format
        this._join = str2Date(date);
    }

    public String toString() { //tostring separates the value with comma
        return get_id() + "," + get_name() + "," + get_address()+ ","
                + get_joinDate() + "," + get_expireDate();
    }
    
    public boolean equals(Member member){ //method to comapre two members by id
        return (_id == member.get_id() && _name.equals(member.get_name()));
    }

    public String toDisplay() { //extended version of to Display for more userfriendly format to be developed later
        return get_id() + "," + get_name() + "," + get_address()
                + get_joinDate() + "," + get_expireDate();
    }

    public LocalDate str2Date(String date) { //method to convert date written as a string to date
        try {
            return LocalDate.parse(date);

        } catch (NumberFormatException e) {
            System.err.println("Date can not be converted in a usable format. Please enter"
                    + "a date in style of YYYY-MM-DD");

        }
        return null;
    }
}
