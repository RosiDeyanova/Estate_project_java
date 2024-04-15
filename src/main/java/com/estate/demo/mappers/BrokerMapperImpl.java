package com.estate.demo.mappers;

import com.estate.demo.models.Broker;
import com.estate.demo.viewModels.BrokerViewModel;
import org.springframework.stereotype.Service;

@Service
public class BrokerMapperImpl implements BrokerMapper {

    @Override
    public Broker BrokerVMToBroker(BrokerViewModel brokerViewModel) {
        Broker broker = new Broker();
        broker.setFirstName(brokerViewModel.getFirstName());
        broker.setLastName(brokerViewModel.getLastName());
        broker.setEmail(brokerViewModel.getEmail());
        broker.setPhoneNumber(brokerViewModel.getPhoneNumber());
        broker.setPassword(brokerViewModel.getPassword());

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
