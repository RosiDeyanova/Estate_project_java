package com.estate.demo.mappers;

import com.estate.demo.models.Broker;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.security.PasswordConfig;
import com.estate.demo.viewModels.BrokerViewModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BrokerMapperImpl implements BrokerMapper {
   private final PasswordConfig passwordConfig;
   private final EstateRepository estateRepository;

    @Override
    public Broker BrokerVMToBroker(BrokerViewModel brokerViewModel) {
        Broker broker = new Broker();
        broker.setFirstName(brokerViewModel.getFirstName());
        broker.setLastName(brokerViewModel.getLastName());
        broker.setEmail(brokerViewModel.getEmail());
        broker.setPhoneNumber(brokerViewModel.getPhoneNumber());
        broker.setPassword(passwordConfig.hashPassword(brokerViewModel.getPassword()));
        //implemented password encoding

        return broker;
    }

    @Override
    public BrokerViewModel BrokerToBrokerVM(Broker broker) {

        BrokerViewModel brokerViewModel = new BrokerViewModel();
        brokerViewModel.setId(broker.getId());
        brokerViewModel.setFirstName(broker.getFirstName());
        brokerViewModel.setLastName(broker.getLastName());
        brokerViewModel.setEmail(broker.getEmail());
        brokerViewModel.setPhoneNumber(broker.getPhoneNumber());
        brokerViewModel.setPassword(broker.getPassword());

        brokerViewModel.setEstatesCount((estateRepository.findAllByBroker(broker).size()));
        return brokerViewModel;
    }
}
