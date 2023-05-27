package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public class EP {
    public enum AdminRegion {
        Addis_Ababa {
            @Override
            public String toString() {
                return "Addis Ababa";
            }
        },
        Afar,
        Amahara,
        Bensishagul_Gumuz {
            @Override
            public String toString() {
                return "Bensishagul Gumuz";
            }
        },
        Dire_Dawa {
            @Override
            public String toString() {
                return "Dire Dawa";
            }
        },
        Harari,
        Gambella,
        Oromia,
        Somali,
        SWR,
        South,
        Sidama,
        Tigray


    }

    public enum RNC {
        RNC001,
        RNC002,
        RNC003,
        RNC004,
        RNC005,
        RNC006,
        RNC007,
        RNC008,
        RNC009,
        RNC010,
        RNC011,
        RNC101,
        RNC102,
        RNC103,
        RNC104,
        RNC105,
        RNC106,
        RNC107,
        RNC108,
        RNC109,
        RNC110,
        RNC111,
        RNC112,
        RNC113,
        RNC114,
        RNC501,
        RNC502,
        RNC503
    }

    public enum BSC {
        NRBSC01,
        NRBSC02,
        NERBSC01,
        NWRBSC02,
        AABSC01,
        CWRBSC601,
        NWRBSC03,
        NWRBSC04,
        NNWRBSC01,
        AABSC02,
        AABSC03,
        AABSC04,
        AABSC05,
        CNRBSC01,
        SSWRBSC01,
        SSWRBSC02,
        NERBSC02,
        CWRBSC602,
        ERBSC01,
        ERBSC02,
        EERBSC01,
        EERBSC02,
        SSWRBSC03,
        SSWRBSC04,
        SRBSC408,
        SRBSC409,
        SRBSC410,
        SRBSC411,
        SRBSC412,
        SRBSC413,
        SRBSC414,
        SERBSC401,
        SERBSC402,
        SERBSC403,
        SERBSC404,
        SERBSC405,
        SERBSC406,
        SERBSC407,
        SWRBSC416,
        SWRBSC417,
        SWRBSC418,
        SWRBSC419,
        SWRBSC420,
        WRBSC603,
        WRBSC604,
        WRBSC605


    }

    public enum Region {
        NR,
        NER,
        NEER,
        NWR,
        NNWR,
        CNR,
        AA,
        SER,
        SSWR,
        SWR,
        SR,
        WR,
        WWR,
        CWR,
        ER,
        EER
    }

    public enum SiteAreaType {
        RoofTop,
        GreenField
    }

    public enum SiteType {
        OUTDOOR,
        INDOOR
    }

    public enum Vendor {
        Huawei,
        Ericsson,
        ZTE
    }

    public enum SiteClass {
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE;

        @JsonValue
        public int toValue() {
            return ordinal();
        }

    }

    public enum Band {
        GSM900,
        DCS1800,
        U900,
        U2100,
        L1800,
        L2600
    }

    public enum RAT {
        GSM,
        UMTS,
        LTE
    }



}
