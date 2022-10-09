import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import HomePage from './HomePage'

//Testing file for HomePage.tsx

//Clears the buffer after each test
afterEach(() => { 
  cleanup();
})

//1 test included in below description
describe("Homepage Component" , () => {

  //Test 1: looking for the text "TheBuzz: Home Page"
  test('Render HomePage', () => {
    render(<HomePage />);
    const linkElement = screen.getByText('TheBuzz: Home page');
    expect(linkElement).toBeInTheDocument();
  }) 
  
})