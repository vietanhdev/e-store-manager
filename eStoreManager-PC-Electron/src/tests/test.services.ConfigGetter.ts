import { ConfigGetter } from '../services/ConfigGetter';
import { expect } from 'chai';
import 'electron-mocha';

describe('Test ConfigGetter', () => {

  it('Test return type of getInstance()', () => {
    const configGetter = ConfigGetter.getInstance();
    expect(configGetter).to.be.a('object');
  });

  it('Test return type get() function', () => {
    const configGetter = ConfigGetter.get();
    expect(configGetter).to.be.a('object');
  });

  it('Check properties "languages"', () => {
    const config = ConfigGetter.get();
    expect(config).to.have.property('languages');
  });

  it('Check properties "barcode_server"', () => {
    const config = ConfigGetter.get();
    expect(config).to.have.property('barcode_server');
  });

  

});