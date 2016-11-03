package clockAngle;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClockController {
	
	private Angle angle;
	private Clock clock;
	private static Map<Clock,Angle> mapHour;
	
	static{
		mapHour = new HashMap<Clock,Angle>();
	}
	
		 
	/**
	 * GET Angle pela hora.
	 * @param hour Hora fornecida para calculo do angulo
	 * @return ResponseEntity<> Entidade de resposta HTTP contendo cabecalho, corpo e um codigo status HTTP 
	 */
	  @RequestMapping(value = "/rest/clock/{hour}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseEntity<?> getAngle(@PathVariable(value="hour") int hour){
	    	this.angle = new Angle();
	    	
	    	try {
	    		
	    		if(hour < 0 || hour > 23){
	    			throw new Exception("Valor de hora inválido. Por favor, escolha um valor entre 0 e 23.");
	    		}
	    			    		
	    		this.clock = new Clock(hour,0);
		    	
		    	if(mapHour.containsKey(clock)){
		    		this.angle.setAngle((mapHour.get(clock)).getAngle());
		    	}
		    	else{
		    		this.calculateAngle(clock);
		    	}  
		    	
		    	return new ResponseEntity<Angle>(this.angle, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Error>(new Error(500, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    	
		}
	    
	  /**
	   * GET Angle pela hora e minuto.
	   * @param hour Hora fornecida para calculo do angulo
	   * @param minute Minuto fornecido para calculo do angulo
	   * @return ResponseEntity<> Entidade de resposta HTTP contendo cabecalho, corpo e um codigo status HTTP 
	   */
	    @RequestMapping(value = "/rest/clock/{hour}/{minute}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	    public ResponseEntity<?> getAngle(@PathVariable(value="hour") int hour, @PathVariable(value="minute") int minute){
	    	this.angle = new Angle();
	    	
	    	try {
	    	
	    		if(hour < 0 || hour > 23){
	    			throw new Exception("Valor de hora inválido. Por favor, escolha um valor entre 0 e 23.");
	    		}
	    		else if(minute < 0 || minute > 60){
	    			throw new Exception("Valor de hora inválido. Por favor, escolha um valor entre 0 e 60.");
	    		}
	    		
		    	this.clock = new Clock(hour,minute);
		    	
		    	if(mapHour.containsKey(clock)){
		    		this.angle.setAngle(mapHour.get(clock).getAngle());
		    	}
		    	else{
		    		this.calculateAngle(clock);
		    	}     	
		    	
		    	return new ResponseEntity<Angle>(this.angle, HttpStatus.OK);
	    	} catch (Exception e) {
				return new ResponseEntity<Error>(new Error(500, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	/**
	 * Metodo para calcular angulo entre dois ponteiros de um relogio    
	 * @param clock Objeto contendo hora e minuto referentes aos ponteiros
	 */
    private void calculateAngle(Clock clock){
    	int hour_Min;
    	int angleHour;
    	
    	if(clock.getHour() > 12){ 
    		hour_Min = (clock.getHour() - 12) * 5;
    	}
    	else {
      		hour_Min = clock.getHour()*5;
    	}
    	//360 graus / 60 min = 6 graus/min 
    	//6 graus - unidade minima 
		angleHour = Math.abs((hour_Min - clock.getMinute())) * 6;	
		
		if (angleHour > 180) {
			angleHour = 360 - angleHour;
		}
		this.angle.setAngle(angleHour);
		mapHour.put(clock, this.angle);
     }
  
}
