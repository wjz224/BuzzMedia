import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import LoginPage from './LoginPage'

//Testing file for HomePage.tsx

//Clears the buffer after each test
afterEach(() => { 
  cleanup();
})

//1 test included in below description
describe("Homepage Component" , () => {

  test('Render HomePage', () => {
    render(<LoginPage />);
    // const linkElement = screen.getByText('TheBuzz');
    // expect(linkElement).toBeInTheDocument();
  }) 
  
})