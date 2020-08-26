package studiranje.ip.database.controller;

/**
 * Стања у којима може бити избор базе података, односно када је у питању  
 * табела са корисницима. Подразумјевани статус значи локалну yi базу података. 
 * Статус базе података значи једну фиксну базу података. А размјене значе 
 * избор база података из листе од више база података које су наведене 
 * на серверу.  
 * @author mirko
 * @version 1.0
 */
public enum DatabaseSwitchState {
	DEFAULT_STATE, DATABASE_STATE, SWITCH_STATE
}
