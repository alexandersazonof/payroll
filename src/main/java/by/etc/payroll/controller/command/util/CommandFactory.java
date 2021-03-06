package by.etc.payroll.controller.command.util;

import by.etc.payroll.controller.command.ActionCommand;
import by.etc.payroll.controller.command.impl.account.*;
import by.etc.payroll.controller.command.impl.admin.*;
import by.etc.payroll.controller.command.impl.card.*;
import by.etc.payroll.controller.command.impl.general.*;
import by.etc.payroll.controller.command.impl.transfer.SendAccountMoneyCommand;
import by.etc.payroll.controller.command.impl.transfer.SendCardMoneyCommand;
import by.etc.payroll.controller.command.impl.transfer.TransferAccountMoneyPageCommand;
import by.etc.payroll.controller.command.impl.transfer.TransferMoneyCardCommand;
import by.etc.payroll.controller.command.impl.user.EditUserCommand;
import by.etc.payroll.controller.command.impl.user.EditUserPageCommand;
import by.etc.payroll.controller.command.impl.user.LogoutCommand;
import by.etc.payroll.controller.command.impl.user.UserMainPageCommand;

public enum CommandFactory {
    LOGIN {
        public ActionCommand createCommand() {
            return new LoginCommand();
        }
    },
    LOGOUT {
        public ActionCommand createCommand() {
            return new LogoutCommand();
        }
    },
    SIGNUP {
        public ActionCommand createCommand() {
            return new SignUpCommand();
        }
    },
    SHOWCARDPAGE {
        public ActionCommand createCommand() {return new ShowCardPageCommand(); }
    },
    NEWCARD {
        public ActionCommand createCommand() {return new NewCardCommand(); }
    },
    CHANGELANGUAGE {
        public ActionCommand createCommand() {return new ChangeLanguageCommand();}
    },
    SIGNUPPAGE {
        @Override
        public ActionCommand createCommand() {
            return new SignUpPageCommand();
        }
    },
    LOGINPAGE {
        @Override
        public ActionCommand createCommand() {
            return new LoginPageCommand();
        }
    },
    NEWACCOUNTPAGE {
        @Override
        public ActionCommand createCommand() {
            return new NewAccountPageCommand();
        }
    },
    MAINPAGE {
        @Override
        public ActionCommand createCommand() {
            return new ViewMainPageCommand();
        }
    },
    ERROR {
        @Override
        public ActionCommand createCommand() {
            return new ErrorCommand();
        }
    },
    EDITUSERPAGE {
        @Override
        public ActionCommand createCommand() {
            return new EditUserPageCommand();
        }
    },
    EDITUSER {
        @Override
        public ActionCommand createCommand() {
            return new EditUserCommand();
        }
    },
    USERMAINPAGE {
        @Override
        public ActionCommand createCommand() {
            return new UserMainPageCommand();
        }
    },
    NEWCARDPAGE {
        @Override
        public ActionCommand createCommand() {
            return new NewCardPageCommand();
        }
    },
    BLOCKCARD {
        @Override
        public ActionCommand createCommand() {
            return new BlockCardCommand();
        }
    },
    UNBLOCKCARD {
        @Override
        public ActionCommand createCommand() {
            return new UnblockCardCommand();
        }
    },
    DELETECARD {
        @Override
        public ActionCommand createCommand() {
            return new DeleteCardCommand();
        }
    },
    TRANSFERMONEY {
        @Override
        public ActionCommand createCommand() {
            return new TransferMoneyCardCommand();
        }
    },
    SENDCARDMONEY {
        @Override
        public ActionCommand createCommand() {
            return new SendCardMoneyCommand();
        }
    },
    NEWACCOUNT {
        @Override
        public ActionCommand createCommand() {
            return new NewAccountCommand();
        }
    },
    SHOWACCOUNT {
        @Override
        public ActionCommand createCommand() {
            return new ShowAccountPageCommand();
        }
    },
    BLOCKACCOUNT {
        @Override
        public ActionCommand createCommand() {
            return new BlockAccountCommand();
        }
    },
    UNBLOCKACCOUNT {
        @Override
        public ActionCommand createCommand() {
            return new UnBlockAccount();
        }
    },
    DELETEACCOUNT {
        @Override
        public ActionCommand createCommand() {
            return new DeleteAccountCommand();
        }
    },
    TRANSFERACCOUNTMONEY {
        @Override
        public ActionCommand createCommand() {
            return new TransferAccountMoneyPageCommand();
        }
    },
    SENDACCOUNTMONEY {
        @Override
        public ActionCommand createCommand() {
            return new SendAccountMoneyCommand();
        }
    },
    ADMINPAGE {
        @Override
        public ActionCommand createCommand() {
            return new AdminMainPageCommand();
        }
    },
    HISTORYTRANSFER {
        @Override
        public ActionCommand createCommand() {
            return new HistoryTransferPageCommand();
        }
    },
    RATEPAGE {
        @Override
        public ActionCommand createCommand() {
            return new RatesPageCommand();
        }
    },
    EDITRATEPAGE {
        @Override
        public ActionCommand createCommand() {
            return new EditRatePageCommand();
        }
    },
    SAVERATE {
        @Override
        public ActionCommand createCommand() {
            return new SaveRateCommand();
        }
    },
    NEWRATEPAGE {
        @Override
        public ActionCommand createCommand() {
            return new NewRatePageCommand();
        }
    },
    ADDRATE {
        @Override
        public ActionCommand createCommand() {
            return new AddRateCommand();
        }
    },
    DELETERATE {
        @Override
        public ActionCommand createCommand() {
            return new DeleteRateCommand();
        }
    },
    VALUTEPAGE {
        @Override
        public ActionCommand createCommand() {
            return new ValutePageCommand();
        }
    },
    EDITVALUTEPAGE {
        @Override
        public ActionCommand createCommand() {
            return new EditValutePageCommand();
        }
    },
    EDITVALUTE {
        @Override
        public ActionCommand createCommand() {
            return new EditValuteCommand();
        }
    },
    APPLICATION {
        @Override
        public ActionCommand createCommand() {
            return new HistoryApplicationCommand();
        }
    };

    public abstract ActionCommand createCommand();
}
