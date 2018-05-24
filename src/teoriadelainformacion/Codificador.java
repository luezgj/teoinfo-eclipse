package teoriadelainformacion;

/**
 *
 * @author Nicolas Rago
 */
import java.util.HashMap;
import java.util.List;


public class Codificador {

	public static String generarBits(char num)
	{
		String salida="";
		char mascara = 1 << 15;
		for(int i = 0; i < 16; i++)
		{
			if((num& mascara)==32768) 
				salida+="1";
			else
				salida+="0";
			num = (char) (num << 1);
		}
		return salida;
	}
	
	public static <T> String codificar(List<T> mensaje, HashMap<T,String[]> codificacion )
	{
            

                StringBuilder resultado= new StringBuilder();
		char buffer = 0;
		int cant_digitos = 0;
		for (T simbolo : mensaje) {
			String[] codigo = codificacion.get(simbolo);
			for (String bit : codigo) {
				
					buffer = (char) (buffer << 1);
					if(bit.equals("1"))
					{
						buffer = (char) (buffer | 1);
					}
					cant_digitos ++;
					if(cant_digitos == 16){
						resultado.append(buffer);
						buffer = 0;
						cant_digitos=0;
					}
                                       
				}
		}
		
		if((cant_digitos<16) &&(cant_digitos !=0)){
			buffer = (char) (buffer <<(16-cant_digitos));
			resultado.append(buffer);
		}
		
               
                
                
		return resultado.toString();
	}
        
}
