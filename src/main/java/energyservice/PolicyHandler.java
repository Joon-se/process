package energyservice;

import energyservice.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PolicyHandler{
    @Autowired
    private ProcessRepository processRepository;

    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverAssigned_Process(@Payload Assigned assigned){

        if(assigned.isMe()){
            System.out.println("##### listener Process : " + assigned.toJson());

            Process newProcess = new Process();
            newProcess.setReservationid(assigned.getReservationid());
            newProcess.setAddress(assigned.getAddress());
            newProcess.setAgentid(assigned.getAgentid());
            newProcess.setMobileno(assigned.getMobileno());
            newProcess.setName(assigned.getName());
            newProcess.setStatus(assigned.getStatus());
            processRepository.save(newProcess);
        }
    }

}
