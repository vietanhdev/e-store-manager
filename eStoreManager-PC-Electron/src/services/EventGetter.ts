export class EventGetter {
    private static instance: EventGetter;
    public data: any;
    private constructor() {
        
    }
    static getInstance() {
        if (!EventGetter.instance) {
            EventGetter.instance = new EventGetter();
            EventGetter.instance.data = require("./data/events.json");
        }
        return EventGetter.instance;
    }
    static get(eventName: string) {
        if (typeof EventGetter.getInstance().data[eventName] != "undefined") {
            return EventGetter.getInstance().data[eventName];
        } else {
            return "unknown_event";
        }
    }
}

