import { RestService } from '../services/RestService';
import { expect } from 'chai';
import 'electron-mocha';

describe('Test RestService', () => {

  it('Creating new instance of RestServer should give a object', () => {
    let restService = new RestService();
    expect(restService).to.be.a('object');
  });

});
