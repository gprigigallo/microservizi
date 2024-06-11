package com.nttdata.cassaapi.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.cassa.server.models.ResponseBase;
import com.nttdata.cassaapi.costanti.Costanti;
import com.nttdata.swagger.cassa.server.api.CassaInfoApi;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class CassaController implements CassaInfoApi {

	@Override
	public ResponseEntity<ResponseBase> pagaOrdine(@Parameter(in = ParameterIn.PATH, description = "id Ordine", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
		ResponseBase responseBase = new ResponseBase();
		responseBase.setSuccess(Boolean.TRUE);
		responseBase.setResultCode(200);
		
		try {
			List<String> newOrders = new ArrayList<>();
			List<String> orders = Files.readAllLines(Paths.get(Costanti.ORDINI_FILE_PATH));
			for (String order : orders) {
				String[] orderSplit = order.split(";");
				if(orderSplit[0].equals(String.valueOf(id)) && orderSplit[3].equals("Inserito")) {
					orderSplit[3] = "Pagato";
				}
				newOrders.add(String.join(";", orderSplit));
			}
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(Costanti.ORDINI_FILE_PATH))) {
				for(String ordine : newOrders) {
					bw.write(ordine);
					bw.newLine();
				}
			}
		} catch (IOException e) {
			responseBase.setErrors(e.getMessage());
			responseBase.setSuccess(Boolean.FALSE);
	        return new ResponseEntity<>(responseBase, HttpStatus.INTERNAL_SERVER_ERROR);	
		}

		return new ResponseEntity<>(responseBase, HttpStatus.OK);
	}

}
