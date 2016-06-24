package ch.audacus.management.test;

public class Test {

	public static void main(final String[] args) {

		Database.get();

		final Management management = new Management();
		management.setId(1);
		management.setName("hello");
		final Relation multiple = new Relation();
		multiple.setId(1);
		multiple.setName("single");
		final Thing parkinglot = new Thing();
		parkinglot.setId(1);
		parkinglot.setName("parkinglot");
		final Thing number = new Thing();
		number.setId(2);
		number.setName("number");
		final Property parkinglotnumber = new Property();
		parkinglotnumber.setId(1);
		parkinglotnumber.setThing(parkinglot);
		parkinglotnumber.setRelation(multiple);
		parkinglotnumber.setType(number);
		parkinglotnumber.setName("parkinglotnumber");

		System.out.println(parkinglotnumber.getType().getId());
		System.out.println(parkinglotnumber.getType());
		System.out.println(parkinglotnumber.get("type"));
	}
}
