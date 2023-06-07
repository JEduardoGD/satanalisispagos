package mx.egd.sat.descargopagoanalizer.deserializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.egd.sat.descargopagoanalizer.daos.xml.Concepto;

public class DietDeserializer extends JsonDeserializer<List<Concepto>> {

	@Override
	public List<Concepto> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JacksonException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(jsonParser);
		List<Concepto> diets = mapper.convertValue(node.findValues("Concepto"), new TypeReference<List<Concepto>>() {
		});
		return diets;
	}

}
