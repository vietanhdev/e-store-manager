import { Dialog } from '../services/Dialog';
import { expect } from 'chai';
import 'electron-mocha';

describe('Test Dialog Service', () => {

  it('Test return type of getInstance()', () => {
    const dialog = Dialog.getInstance();
    expect(dialog).to.be.a('object');
  });

});