package data.constants;

public interface ApplicationConstants {
    interface Commands {
        interface Igramba {
            String PATTERN = "/igramba";
            String BTN_YES = "igrambaBtnYes";
            String BTN_NO = "igrambaBtnNo";
        }

        interface Gulamba {
            String PATTERN = "/gulamba";
            String BTN_YES = "gulambaBtnYes";
            String BTN_NO = "gulambaBtnNo";
            String TO_DANILA = "gulambaToDanilaBtn";
            String TO_VLADIMIR = "gulambaToVladimirBtn";
            String TO_ANVAR = "gulambaToAnvarBtn";
            String TO_DANILA_MSG = "userDecisionDanila";
            String TO_VLADIMIR_MSG = "userDecisionVladimir";
            String TO_ANVAR_MSG = "userDecisionAnvar";
        }
    }
}
