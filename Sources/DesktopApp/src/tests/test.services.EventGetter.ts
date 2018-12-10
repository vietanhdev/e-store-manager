import { EventGetter } from '../services/EventGetter';
import { expect } from 'chai';
import 'electron-mocha';

describe('Test EventGetter', () => {

  it('Test return type of getInstance()', () => {
    const eventGetter = EventGetter.getInstance();
    expect(eventGetter).to.be.a('object');
  });

  it('Test event "request_update_employee_list"', () => {
    let text = EventGetter.get("request_update_employee_list");
    expect(text).to.be.a('string');
  });

  it('Test request "unknown_event"', () => {
    let text = EventGetter.get("no_event_have_key_like_this_jsdui21i");
    expect(text).to.equal('unknown_event');
  });
  

});