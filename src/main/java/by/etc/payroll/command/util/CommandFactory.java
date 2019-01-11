package by.etc.payroll.command.util;

import by.etc.payroll.command.ActionCommand;
import by.etc.payroll.command.impl.account.*;
import by.etc.payroll.command.impl.card.NewCardCommand;
import by.etc.payroll.command.impl.card.NewCardPageCommand;
import by.etc.payroll.command.impl.card.ShowCardPageCommand;
import by.etc.payroll.command.impl.general.*;
import by.etc.payroll.command.impl.transaction.HistoryTransactionPageCommand;
import by.etc.payroll.command.impl.transaction.SendMoneyCommand;
import by.etc.payroll.command.impl.transaction.TransferMoneyCommand;
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
    EDITCARD {
        public ActionCommand createCommand() {return new EditAccountCommand(); }
    },
    SAVECARD {
        public ActionCommand createCommand() {return new SaveAccountCommand(); }
    },
    NEWCARD {
        public ActionCommand createCommand() {return new NewCardCommand(); }
    },
    TRANSFER {
        public ActionCommand createCommand() {return new TransferMoneyCommand();}
    },
    SENDMONEY {
        public ActionCommand createCommand() {return new SendMoneyCommand();}
    },
    DELETEACCOUNT {
        public ActionCommand createCommand() {return new DeleteAccountCommand();}
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
    EDITCARDPAGE {
        @Override
        public ActionCommand createCommand() {
            return new EditAccountPageCommand();
        }
    },
    HISTORYPAGE {
        @Override
        public ActionCommand createCommand() {
            return new HistoryTransactionPageCommand();
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
    };

    public abstract ActionCommand createCommand();
}
