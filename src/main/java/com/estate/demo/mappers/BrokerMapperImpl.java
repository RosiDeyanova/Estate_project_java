package com.estate.demo.mappers;

import com.estate.demo.models.Broker;
import com.estate.demo.security.PasswordConfig;
import com.estate.demo.viewModels.BrokerViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BrokerMapperImpl implements BrokerMapper {
   private final PasswordConfig passwordConfig;

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
        brokerViewModel.setFirstName(broker.getFirstName());
        brokerViewModel.setLastName(broker.getLastName());
        brokerViewModel.setEmail(broker.getEmail());
        brokerViewModel.setPhoneNumber(broker.getPhoneNumber());
        brokerViewModel.setPassword(broker.getPassword());

        return brokerViewModel;
    }
}
