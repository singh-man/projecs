package com.state;
public class Entity {
	
	private enum State{
		
		OPEN{
			void prceed(Entity entity) {
				entity.state = PENDING_APPROVAL;
			}
			
		},
		PENDING_APPROVAL{
			void prceed(Entity entity) {
				entity.state = APPROVED;
			}
			
		},
		APPROVED{
			void prceed(Entity entity) {
				entity.state = CLOSED;
				sendNotifcation(entity);
			}
			
		},
		CLOSED{
			void prceed(Entity entity) {
				throw new RuntimeException("Entity already closed");
			}
		};
		
		abstract void prceed(Entity entity);
	}
	
	private State state = State.OPEN;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void proceed(){
		this.state.prceed(this);
	}
	
	private static void sendNotifcation(Entity entity){}
	
}
