package org.example;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Properties;
import java.util.Scanner;

public class ApacheDSLDAPConnection {
    DirContext connection;
    public void LdapConnection(){
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL,"ldap://localhost:10389");
        env.put(Context.SECURITY_PRINCIPAL,"uid=admin, ou=system");
        env.put(Context.SECURITY_CREDENTIALS,"secret");
            String name = "start";
        try {
            connection = new InitialDirContext(env);
            System.out.println("Established Connection " + connection);
        }
        catch (AuthenticationException aex){
            System.out.println(aex.getMessage());
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllUsers(){
        String searchFilter = "(objectClass=inetOrgPerson)";
        String[] reqAttr = {"cn","sn"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAttr);

        try {
            NamingEnumeration users = connection.search("ou=users,ou=system",searchFilter,controls);
            SearchResult result = null;
            while (users.hasMore()){
                result = (SearchResult) users.next();
                Attributes attributes = result.getAttributes();
                String name = attributes.get("cn").get(0).toString();
                System.out.println(attributes.get("cn")+" "+attributes.get("sn"));
                //addUserToGroup(name,"Administrators");
                deleteUserToGroup(name,"Administrators");
            }

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }


    public void addUser(){
        Attributes attributes = new BasicAttributes();
        Attribute attribute = new BasicAttribute("objectClass");
        attribute.add("inetOrgPerson");

        attributes.put(attribute);

        //user details
        attributes.put("sn","White");
        try {
            connection.createSubcontext("cn=W,ou=users,ou=system",attributes);
            System.out.println("success");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUserToGroup(String username, String groupName){
        ModificationItem[] mods = new ModificationItem[1];
        Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
        mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
        try {
            connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system",mods);
            System.out.println("Success");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(){
        try {
            connection.destroySubcontext("cn=ggg,ou=users,ou=system");
            System.out.println("Success");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserToGroup(String username, String groupName){
        ModificationItem[] mods = new ModificationItem[1];
        Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
        mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
        try {
            connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system",mods);
            System.out.println("Success");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchUsers(){
        String searchFilter = "(|(uid=1)(cn=zedd)(cn=W))";
        String[] reqAttr = {"cn","sn","uid"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAttr);

        try {
            NamingEnumeration users = connection.search("ou=users,ou=system",searchFilter,controls);
            SearchResult result = null;
            while (users.hasMore()){
                result = (SearchResult) users.next();
                Attributes attributes = result.getAttributes();
                String name = attributes.get("cn").get(0).toString();
                System.out.println(attributes.get("cn")+" "+attributes.get("sn")+" "+attributes.get("uid"));
                //addUserToGroup(name,"Administrators");
                //deleteUserToGroup(name,"Administrators");
            }

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean authUser(String username, String password){

        try {

            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL,"ldap://localhost:10389");
            env.put(Context.SECURITY_PRINCIPAL,"cn="+username+",ou=users,ou=system");
            env.put(Context.SECURITY_CREDENTIALS,password);
            DirContext con = new InitialDirContext(env);
            con.close();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }


    public void updateUserPassword(String username, String password){
        try {
            ModificationItem[] mod = new ModificationItem[1];
            mod[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword",password));
            connection.modifyAttributes("cn="+username+",ou=users,ou=system",mod);
            System.out.println("Success");
        }catch (Exception e){
            System.out.println("failed "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("ENTER THE LDAP PARAMETER");
        ApacheDSLDAPConnection apacheDSLDAPConnection = new ApacheDSLDAPConnection();
        // Scanner scanner = new Scanner(System.in);
        apacheDSLDAPConnection.LdapConnection(/*scanner.nextLine()*/);
        /*scanner.close();*/
        //apacheDSLDAPConnection.getAllUsers();
        System.out.println("************");
        //apacheDSLDAPConnection.addUser();
        //apacheDSLDAPConnection.getAllUsers();
      // apacheDSLDAPConnection.deleteUser();
        // apacheDSLDAPConnection.searchUsers();
        // System.out.println(authUser("H","starvingg"));
apacheDSLDAPConnection.updateUserPassword("H","12");

    }
}
