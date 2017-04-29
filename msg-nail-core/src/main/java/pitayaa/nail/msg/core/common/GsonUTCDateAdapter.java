package pitayaa.nail.msg.core.common;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonUTCDateAdapter implements JsonSerializer<Date>,
		JsonDeserializer<Date> {

	private final DateFormat dateFormat;

	public GsonUTCDateAdapter() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
				Locale.US); // This is the format I need
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public synchronized JsonElement serialize(Date date, Type type,
			JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(dateFormat.format(date));
	}

	@Override
	public synchronized Date deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext) {
		Date date = null;
		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
			 date = new Date(jsonElement.getAsLong());
			 gson.toJson(date);
			 //date = dateFormat.parse(timeToString);
		} catch (JsonParseException e) {
			throw new JsonParseException(e);
		}
		return date;
	}

}
