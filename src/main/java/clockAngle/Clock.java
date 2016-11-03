package clockAngle;

public class Clock {

	private int hour;
	private int minute;
			
	public Clock(int hour, int minute){
		this.hour = hour;
		this.minute = minute;
	}		
	
	@Override
    public boolean equals(Object objeto) {
		return objeto instanceof Clock &&  this.hour == ((Clock) objeto).getHour()
			       && this.minute == ((Clock) objeto).getMinute();
    }
	
	@Override
	public int hashCode() {
		return this.hour + this.minute;
	}
	
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	
}
