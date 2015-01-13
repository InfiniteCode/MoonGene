//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.android;

public class EconomyBalanceItem {
    public String id;
    public Long amount;

    public EconomyBalanceItem(String ID, Long Amount) {
        this.id = ID;
        this.amount = Amount;
    }
}
