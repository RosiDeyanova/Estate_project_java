package com.estate.demo.mappers;

import com.estate.demo.models.Customer;
import com.estate.demo.security.PasswordConfig;
import com.estate.demo.viewModels.CustomerViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerMapperImpl implements CustomerMapper {
    private final PasswordConfig passwordConfig;

    @Override
    public Customer CustomerVMToCustomer(CustomerViewModel customerViewModel) {
        Customer customer = new Customer();
        customer.setFirstName(customerViewModel.getFirstName());
        customer.setLastName(customerViewModel.getLastName());
        customer.setEmail(customerViewModel.getEmail());
        customer.setPassword(passwordConfig.hashPassword(customerViewModel.getPassword()));
        return customer;
    }

    @Override
    public CustomerViewModel CustomerToCustomerVM(Customer customer) {
        CustomerViewModel customerVM = new CustomerViewModel();
        customerVM.setFirstName(customer.getFirstName());
        customerVM.setLastName(customer.getLastName());
        customerVM.setEmail(customer.getEmail());
        customerVM.setPassword(customer.getPassword());
        return customerVM;
    }


}
