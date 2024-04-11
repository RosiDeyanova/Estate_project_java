package com.estate.demo.bootstrap;


import com.estate.demo.enums.EstateStatus;
import com.estate.demo.models.Customer;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.CustomerRepository;
import com.estate.demo.repositories.EstateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final EstateRepository estateRepository;

    public BootstrapData(CustomerRepository customerRepository, EstateRepository estateRepository) {
        this.customerRepository = customerRepository;
        this.estateRepository = estateRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        Estate firstEstate = new Estate();
        firstEstate.setName("Rural house");
        firstEstate.setDescription("This charming rural house boasts a serene setting, " +
                "offering a tranquil escape from urban life. With its classic design and spacious layout, " +
                "it provides the perfect canvas for personalized touches.");
        firstEstate.setPrice(123000.0);
        firstEstate.setSize(150);
        firstEstate.setImageName("house3.jpg");


        Estate secondEstate = new Estate();
        secondEstate.setName("City apartment");
        secondEstate.setDescription("Nestled in the heart of the bustling city, " +
                "this modern apartment offers urban dwellers a stylish retreat with " +
                "its sleek design and contemporary amenities. Boasting expansive city " +
                "views from large windows.");
        secondEstate.setPrice(200000.0);
        secondEstate.setSize(80);
        secondEstate.setImageName("house5.jpg");
        secondEstate.setStatus(EstateStatus.Hot);

        Customer ivan = new Customer();
        ivan.setEmail("ivaniv@gmail.com");
        ivan.setPassword("123456");
        ivan.setFirstName("Ivan");
        ivan.setLastName("Nedelchev");

        Customer neda = new Customer();
        neda.setEmail("n45petrova@gmail.com");
        neda.setPassword("123456");
        neda.setFirstName("Neda");
        neda.setLastName("Petrova");

        Estate firstEstateSaved = estateRepository.save(firstEstate);
        Estate secondEstateSaved = estateRepository.save(secondEstate);

        Customer ivanSaved = customerRepository.save(ivan);
        Customer nedaSaved = customerRepository.save(neda);

        ivanSaved.getEstates().add(firstEstateSaved);
        ivanSaved.getEstates().add(secondEstateSaved);
        firstEstateSaved.getCustomers().add(ivanSaved);
        secondEstateSaved.getCustomers().add(ivanSaved);
        customerRepository.save(ivanSaved);
        estateRepository.save(firstEstateSaved);
        estateRepository.save(secondEstateSaved);

        System.out.println("In Bootstrap");
        System.out.println("Estate Count: " + estateRepository.count());
        System.out.println("Customer Count: " + customerRepository.count());

    }
}