package com.estate.demo.mappers;
import com.estate.demo.models.Customer;
import com.estate.demo.viewModels.CustomerViewModel;

public interface CustomerMapper {
    public Customer CustomerVMToCustomer(CustomerViewModel customerViewModel);

    public CustomerViewModel CustomerToCustomerVM(Customer customer);
}
