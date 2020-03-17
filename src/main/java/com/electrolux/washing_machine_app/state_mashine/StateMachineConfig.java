package com.electrolux.washing_machine_app.state_mashine;

import com.electrolux.washing_machine_app.state_mashine.enums.Events;
import com.electrolux.washing_machine_app.state_mashine.enums.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import static com.electrolux.washing_machine_app.state_mashine.enums.Events.*;
import static com.electrolux.washing_machine_app.state_mashine.enums.States.*;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration().autoStartup(true);
    }


    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(READY)
                .state(WORKING)
                .state(PAUSED)
                .end(FINISHED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(READY)
                .target(WORKING)
                .event(START_PROGRAM)
                .and()
                .withExternal()
                .source(WORKING)
                .target(PAUSED)
                .event(PAUSE_PROGRAM)
                .and()
                .withExternal()
                .source(PAUSED)
                .target(WORKING)
                .event(CONTINUE_PROGRAM)
                .and()
                .withExternal()
                .source(WORKING)
                .target(FINISHED)
                .event(FINISH_PROGRAM);
    }
}
