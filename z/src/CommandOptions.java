import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;


public enum CommandOptions {

	//Singleton instance to access actual commands
	INSTANCE;

	private enum CLIOptions{

		//Declare all the CLI switches/commands here 
		USER("u",true,1,"user-name","user", "enter user name"),
		PASSWORD("p",true,1,"password","pwd", "enter password"),
		SERVER("s",true,1,"server-IP/NAME","server", "server address here"),
		ACTIVATE("a",false,3,"to-activate","activate", "activate conig|server|collecton"),
		DEACTIVATE("d",false,3,"to-de-activate","de-activate", "de-activate conig|server|collecton");

		private String shortCommand;
		private boolean isMandatory;
		private int numArgs;
		private String argName;
		private String longCommand;
		private String description;
		
		/**
		 * 
		 * @param shortCommand: short name of the switch
		 * @param isMandatory: is a mandatory switch
		 * @param numArgs: number of args this switch can have(0 if no args with switch)
		 * @param argName: name of the arg(displayed in help usage)
		 * @param longCommand: long name for the switch
		 * @param description: description of the command
		 */
		private CLIOptions(String shortCommand, boolean isMandatory,
				int numArgs, String argName,
				String longCommand, String description) {
			this.shortCommand = shortCommand;
			this.isMandatory = isMandatory;
			this.numArgs = numArgs;
			this.argName = argName;
			this.longCommand = longCommand;
			this.description = description;
		}

		
	}

	Option makeOption(CLIOptions cmd){
		return OptionBuilder.
		withArgName(cmd.argName).
		isRequired(cmd.isMandatory).
		hasArgs(cmd.numArgs).
		withLongOpt(cmd.longCommand).
		withDescription(cmd.description).
		create(cmd.shortCommand);
		//return new Option(cmd.shortCommand, cmd.longCommand, cmd.hasArgs, cmd.description);
	}

	public Options getOptions(){
		
		Options options = new Options();
		CLIOptions[] cli = CLIOptions.values();
		for(int i = 0; i < cli.length; i++)
			options.addOption(makeOption(cli[i]));
		return options;
	}

}
