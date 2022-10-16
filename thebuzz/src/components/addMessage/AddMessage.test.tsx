import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import AddMessage from './AddMessage';

//Testing file for AddMessage.tsx

//Clears buffer after each test
afterEach(() => { 
  cleanup();
})

//1 test included in below description
describe("AddMessage Component" , () => {

  //Test 1: lokking for the text "Add Message"
  test('Render text for add message', () => {
  render(<AddMessage />);
  const linkElement = screen.getByText('Add Message:');
  expect(linkElement).toBeInTheDocument();
  
})
})