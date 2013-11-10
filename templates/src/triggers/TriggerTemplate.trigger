trigger {{class_name}}Trigger on {{object_name}} (after delete, after insert, after undelete, after update, before delete, before insert, before update) {
	{{class_name}}TriggerHandler handler = new {{class_name}}TriggerHandler(Trigger.isExecuting, Trigger.size);
	
	if(Trigger.isInsert && Trigger.isBefore){
		handler.OnBeforeInsert(Trigger.new);
	}
	else if(Trigger.isInsert && Trigger.isAfter){
		handler.OnAfterInsert(Trigger.new);
		{{class_name}}TriggerHandler.OnAfterInsertAsync(Trigger.newMap.keySet());
	}
	
	else if(Trigger.isUpdate && Trigger.isBefore){
		handler.OnBeforeUpdate(Trigger.old, Trigger.new, Trigger.newMap);
	}
	else if(Trigger.isUpdate && Trigger.isAfter){
		handler.OnAfterUpdate(Trigger.old, Trigger.new, Trigger.newMap);
		{{class_name}}TriggerHandler.OnAfterUpdateAsync(Trigger.newMap.keySet());
	}
	
	else if(Trigger.isDelete && Trigger.isBefore){
		handler.OnBeforeDelete(Trigger.old, Trigger.oldMap);
	}
	else if(Trigger.isDelete && Trigger.isAfter){
		handler.OnAfterDelete(Trigger.old, Trigger.oldMap);
		{{class_name}}TriggerHandler.OnAfterDeleteAsync(Trigger.oldMap.keySet());
	}
	
	else if(Trigger.isUnDelete){
		handler.OnUndelete(Trigger.new);	
	}
}