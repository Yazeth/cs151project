import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RecurringEventCreator {

	private static final int[] MONTHS = new int[] { Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL,
			Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER,
			Calendar.NOVEMBER, Calendar.DECEMBER };

	private ArrayList<String[]> eventInputs;

	public RecurringEventCreator(ArrayList<String[]> eventInputs) {
		this.eventInputs = eventInputs;
	}

	/**
	 * Method that creates recurring events 
	 * Order of Strings in inputs: eventName; year; startMonth; endMonth; sequence; startHour; endHour
	 * 
	 * @return
	 */
	public ArrayList<Event> createRecurringEvents() {
		
		String weekSequence = "SMTWHFA";	//Used to check if event recurs on specific days
		
		ArrayList<Event> events = new ArrayList<Event>();	//List of events

		for (String[] inputs : eventInputs) { // For every recurring event inputted

			if (inputs.length != 7) // Check if valid number of inputs were given
				return null;

			String eventName = inputs[0];
			int year = Integer.parseInt(inputs[1]);
			int startMonth = Integer.parseInt(inputs[2]);
			int endMonth = Integer.parseInt(inputs[3]);
			String eventSequence = inputs[4];
			int startHour = Integer.parseInt(inputs[5]);
			int endHour = Integer.parseInt(inputs[6]);

			// Create starting date & time for first event
			Calendar eventStartDate = Calendar.getInstance();
			eventStartDate.set(Calendar.YEAR, year);
			eventStartDate.set(Calendar.MONTH, MONTHS[startMonth]);
			eventStartDate.set(Calendar.DAY_OF_MONTH, 1);
			eventStartDate.set(Calendar.HOUR_OF_DAY, startHour);

			// Create ending date & time for first event
			Calendar eventEndDate = (Calendar) eventStartDate.clone(); // All data is primitive, so no need for
																		// implementing deep clone
			eventEndDate.set(Calendar.HOUR_OF_DAY, endHour);

			boolean[] hasEvent = new boolean[7]; // Checks for recurring event on day of week

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy"); // Formats date tfor creating event

			for (int i = 0; i < weekSequence.length(); i++) { // For each day of the week
				if (eventSequence.contains(String.valueOf(weekSequence.charAt(i))))
					hasEvent[i] = true;
			}

			for (int afterEndMonth = (Integer.parseInt(inputs[3]) + 1) % 12;		//For each day between starting and ending month
					eventEndDate.get(Calendar.MONTH) != afterEndMonth; 
					eventStartDate.add(Calendar.DAY_OF_YEAR, 1), eventEndDate.add(Calendar.DAY_OF_YEAR, 1)) {
				if (hasEvent[eventStartDate.get(Calendar.DAY_OF_WEEK)]) {
					events.add(new Event(eventName, formatter.format(eventStartDate),
							eventStartDate.get(Calendar.HOUR_OF_DAY) + "",
							eventEndDate.get(Calendar.HOUR_OF_DAY) + ""));
					break;
				}

			}
		}
		return events;
	}
	
}
