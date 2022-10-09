import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import EditMsg from './EditMsg'

//Testing file for EditMsg.tsx

//Clears buffer after each test
afterEach(() => { 
  cleanup();
})

//1 test included in below description
describe("EditMsg Component" , () => {

  //Test 1: looking for the text "Update Message"
  test('Render update message', () => {
    render(<EditMsg />);
    const linkElement = screen.getByText('Update Message:');
    expect(linkElement).toBeInTheDocument();
  }) 
})