import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

public class DBUtil {
    @PersistenceContext
    EntityManager em;

    public DBUtil() {
    }


    public void savePerson(Person person) {
        try (Session session = DbSession.getSessionFactory()) {
            Transaction transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    public List<Person> loadPersons() {
        try (Session session = DbSession.getSessionFactory()) {
            Query select_from_person = session.createQuery("from Person");
            return (List<Person>) select_from_person.list();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void saveAddress(Address address) {
        try (Session session = DbSession.getSessionFactory()) {
            Transaction transaction = session.beginTransaction();
            session.save(address);
            transaction.commit();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    public List<Address> loadAddresses() {
        try (Session session = DbSession.getSessionFactory()) {
            Query select_from_person = session.createQuery("from Address");
            return (List<Address>) select_from_person.list();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void saveCountry(Country country) {
        try (Session session = DbSession.getSessionFactory()) {
            Transaction transaction = session.beginTransaction();
            List<Country> countries = getCountriesByName(country, session);
            session.save(country);
            transaction.commit();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    public List<Country> loadCountries() {
        try (Session session = DbSession.getSessionFactory()) {
            Query select_from_person = session.createQuery("from Country");
            return (List<Country>) select_from_person.list();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void saveData(final Person person, final Country country, final Address address) {
        try (Session session = DbSession.getSessionFactory()) {
            Transaction transaction = session.beginTransaction();
            List<Country> countries = getCountriesByName(country, session);
            if (!existsCountryInTable(countries)) {
                savePerson(person);
                saveCountry(country);
                address.setPerson(person);
                address.setCountry(country);
                session.save(address);
                transaction.commit();
            } else {
                address.setPerson(person);
                address.setCountry(getCountryByName(country, session));
                session.save(address);
            }
        } catch (Exception e ) {
            System.out.println(e.getMessage());

        }
    }

    private boolean existsCountryInTable(List<Country> countries) {
        return countries.size() > 0;
    }

    private List<Country> getCountriesByName(Country country, Session session) {
        String queryString = "from Country c where c.countryName = :countryName";
        return getCountryQueryByName(country, session, queryString).list();
    }

    private Query<Country> getCountryQueryByName(Country country, Session session, String queryString) {
        return session.createQuery(queryString, Country.class)
                .setParameter("countryName", country.getCountryName());
    }

    private Country getCountryByName(final Country country, Session session) {
        String queryString = "from Country c where c.countryName = :countryName";
        return getCountryQueryByName(country, session, queryString).getSingleResult();
    }


}
