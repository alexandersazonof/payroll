package by.etc.payroll.command.util;

import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.impl.account.*;
import by.etc.payroll.command.impl.card.*;
import by.etc.payroll.command.impl.general.*;
import by.etc.payroll.command.impl.transfer.SendCardMoneyCommand;
import by.etc.payroll.command.impl.transfer.TransferMoneyCardCommand;
import by.etc.payroll.command.impl.user.EditUserCommand;
import by.etc.payroll.command.impl.user.EditUserPageCommand;
import by.etc.payroll.command.impl.user.LogoutCommand;
import by.etc.payroll.command.impl.user.UserMainPageCommand;

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
    };

    public abstract ActionCommand createCommand();
}
