package com.pkrete.marc.record.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pkrete.marc.record.model.MarcRecord;
import com.pkrete.marc.record.model.MarcRecordCollection;
@Path("/")
public interface RestEndpoint {
	@POST
	@Path("/record")
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML })
	public MarcRecord doRecord(MarcRecord record);

	@POST
	@Path("/records")
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML })
	public MarcRecordCollection doRecords(MarcRecordCollection collection);
}
