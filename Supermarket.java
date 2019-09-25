/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pl21;

import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author André Novo
 */
public class Supermarket {

    Map<Invoice, Set<Product>> m;

    Supermarket() {
        m = new HashMap<>();
    }

    // Reads invoices from a list of String
    void getInvoices(List<String> l) throws Exception {
        Invoice in = null;
        Set<Product> pList = null;
        for (String str : l) {
            String[] clean = str.split(",");
            if (str.charAt(0) == 'I') {
                if (in != null) {
                    m.put(in, pList);
                }
                in = new Invoice(clean[1].trim(), clean[2].trim());
                pList = new HashSet<>();
            } else {
                pList.add(new Product(clean[1].trim(), Integer.parseInt(clean[2].trim()), Long.parseLong(clean[3].trim())));
            }
        }
        if (in != null) {
            m.put(in, pList);
        }
    }

    // returns a set in which each number is the number of products in the r
    // invoice 
    Map<Invoice, Integer> numberOfProductsPerInvoice() {
        Map<Invoice, Integer> mt2 = new HashMap<>();

        for (Invoice in : m.keySet()) {
            mt2.put(in, m.get(in).size());
        }
        return mt2;
    }

    // returns a Set of invoices in which each date is >d1 and <d2
    Set<Invoice> betweenDates(LocalDate d1, LocalDate d2) {
        Set<Invoice> st2 = new HashSet<Invoice>();

        for (Invoice in : m.keySet()) {
            if (in.getDate() != null && in.getDate().isBefore(d2) && in.getDate().isAfter(d1)) {
                st2.add(in);
            }
        }
        return st2;
    }

    // returns the sum of the price of the product in all the invoices
    long totalOfProduct(String productId) {
        long totalPrice = 0;

        for (Set<Product> proList : m.values()) {
            for (Product pro : proList) {
                if (pro.getIdentification().equals(productId)) {
                    totalPrice += pro.getPrice() * pro.getQuantity();
                }
            }
        }
        return totalPrice;
    }

    // converts a map of invoices and troducts to a map which key is a product 
    // identification and the values are a set of the invoice references 
    // in which it appearss
    Map<String, Set<Invoice>> convertInvoices() {
        Map<String, Set<Invoice>> mt2 = new HashMap<>();
        Set<Product> proList2 = allProducts();
        Set<Invoice> inList = new HashSet<>();

        for (Invoice in : m.keySet()) {
            for (Product pro : proList2) {
                if (pro != null && m.get(in).contains(pro)) {
                    inList.add(in);
                }
                mt2.put(pro.getIdentification(), inList);
            }
        }
        return mt2;
    }

    Set<Product> allProducts() {
        Set<Product> pp = new HashSet<>();
        for (Set<Product> proList : m.values()) {
            for (Product pro : proList) {
                if (pro != null && !pp.contains(pro)) {
                    pp.add(pro);
                }
            }
        }
        return pp;
    }

}
