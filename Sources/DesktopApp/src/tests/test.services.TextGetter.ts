import { TextGetter } from '../services/TextGetter';
import { expect } from 'chai';
import 'electron-mocha';

describe('Test TextGetter', () => {

  it('Test return type of getInstance()', () => {
    let textGetter = TextGetter.getInstance();
    expect(textGetter).to.be.a('object');
  });


  it('Test get text without failback - (key,value) = ("program_name", "eStoreManager")', () => {
    let text = TextGetter.get("program_name");
    expect(text).to.equal("eStoreManager");
  });


  it('Test get text with failback - (key,value) = ("program_name", "eStoreManager")', () => {
    let text = TextGetter.get("program_name");
    expect(text).to.equal("eStoreManager");
  });


  it('Test get text with failback - (key,value) = ("no_text_have_key_like_this_jsdui21i", "unknown_text")', () => {
    let text = TextGetter.get("no_text_have_key_like_this_jsdui21i");
    expect(text).to.equal("unknown_text");
  });
  

});
