package com.estate.demo.bootstrap;


import com.estate.demo.enums.EstateStatus;
import com.estate.demo.models.Customer;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.CustomerRepository;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.security.PasswordConfig;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final EstateRepository estateRepository;
    private final PasswordConfig passwordConfig;

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
        ivan.setPassword(passwordConfig.hashPassword("1234567"));
        ivan.setFirstName("Ivan");
        ivan.setLastName("Nedelchev");

        Customer neda = new Customer();
        neda.setEmail("n45petrova@gmail.com");
        neda.setPassword(passwordConfig.hashPassword("123456"));
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

        //generate 10 more estates

        Estate estate1 = new Estate();
        estate1.setName("Cozy Cottage");
        estate1.setDescription("Nestled in the heart of the countryside, this cozy cottage offers " +
                "a peaceful retreat surrounded by nature.");
        estate1.setPrice(95000.0);
        estate1.setSize(100);
        estate1.setImageName("house3.jpg");
        estateRepository.save(estate1);

        Estate estate2 = new Estate();
        estate2.setName("Luxury Villa");
        estate2.setDescription("Experience luxury living in this stunning villa, boasting " +
                "spectacular views and exquisite amenities.");
        estate2.setPrice(2500000.0);
        estate2.setSize(500);
        estate2.setImageName("house3.jpg");
        estateRepository.save(estate2);

        Estate estate3 = new Estate();
        estate3.setName("Seaside Bungalow");
        estate3.setDescription("Enjoy breathtaking ocean views from this charming seaside bungalow, " +
                "perfect for beach lovers seeking tranquility.");
        estate3.setPrice(180000.0);
        estate3.setSize(120);
        estate3.setImageName("house3.jpg");
        estateRepository.save(estate3);

        Estate estate4 = new Estate();
        estate4.setName("Mountain Retreat");
        estate4.setDescription("Escape to the mountains in this cozy retreat, " +
                "surrounded by majestic peaks and serene forests.");
        estate4.setPrice(135000.0);
        estate4.setSize(80);
        estate4.setImageName("house3.jpg");
        estateRepository.save(estate4);

        Estate estate5 = new Estate();
        estate5.setName("Urban Loft");
        estate5.setDescription("Live in style in this modern urban loft, featuring " +
                "sleek design and city skyline views.");
        estate5.setPrice(320000.0);
        estate5.setSize(200);
        estate5.setImageName("house3.jpg");
        estateRepository.save(estate5);

        Estate estate6 = new Estate();
        estate6.setName("Country Farmhouse");
        estate6.setDescription("Experience country living at its finest in this " +
                "charming farmhouse surrounded by rolling hills and lush fields.");
        estate6.setPrice(210000.0);
        estate6.setSize(180);
        estate6.setImageName("house3.jpg");
        estateRepository.save(estate6);

        Estate estate7 = new Estate();
        estate7.setName("Lakefront Cabin");
        estate7.setDescription("Unwind by the tranquil waters of the lake in this " +
                "rustic cabin, offering unparalleled serenity.");
        estate7.setPrice(155000.0);
        estate7.setSize(150);
        estate7.setImageName("house3.jpg");
        estateRepository.save(estate7);

        Estate estate8 = new Estate();
        estate8.setName("City Penthouse");
        estate8.setDescription("Elevate your lifestyle in this luxurious city penthouse, " +
                "boasting stunning skyline views and sophisticated amenities.");
        estate8.setPrice(480000.0);
        estate8.setSize(300);
        estate8.setImageName("house3.jpg");
        estateRepository.save(estate8);

        Estate estate9 = new Estate();
        estate9.setName("Historic Mansion");
        estate9.setDescription("Step back in time in this grand historic mansion, " +
                "filled with elegant charm and timeless beauty.");
        estate9.setPrice(1500000.0);
        estate9.setSize(700);
        estate9.setImageName("house3.jpg");
        estateRepository.save(estate9);

        Estate estate10 = new Estate();
        estate10.setName("Desert Oasis");
        estate10.setDescription("Discover your oasis in the desert with this " +
                "stunning property, featuring lush gardens and a tranquil pool.");
        estate10.setPrice(380000.0);
        estate10.setSize(250);
        estate10.setImageName("house3.jpg");
        estateRepository.save(estate10);

        Estate estate11 = new Estate();
        estate11.setName("Mountain Chalet");
        estate11.setDescription("Escape to the mountains in this charming chalet, " +
                "where cozy interiors and breathtaking views await.");
        estate11.setPrice(280000.0);
        estate11.setSize(180);
        estate11.setImageName("house3.jpg");
        estateRepository.save(estate11);

        Estate estate12 = new Estate();
        estate12.setName("Beachfront Villa");
        estate12.setDescription("Indulge in luxury living with this exquisite beachfront villa, " +
                "offering unparalleled ocean views and lavish amenities.");
        estate12.setPrice(3200000.0);
        estate12.setSize(600);
        estate12.setImageName("house3.jpg");
        estateRepository.save(estate12);



    }
}