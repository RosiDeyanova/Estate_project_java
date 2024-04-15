package com.estate.demo.mappers;

import com.estate.demo.models.Broker;
import com.estate.demo.viewModels.BrokerViewModel;

public interface BrokerMapper {
    public Broker BrokerVMToBroker(BrokerViewModel brokerViewModel);

    public BrokerViewModel BrokerToBrokerVM(Broker broker);
}
