import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import ProfilePage from './ProfilePage'

//Testing file for ProfilePage.tsx

//Clears the buffer after each test
afterEach(() => { 
  cleanup();
})

//1 test included in below description
describe("Homepage Component" , () => {

  //Test 1: looking for the text "TheBuzz: Home Page"
  test('Render ProfilePage', () => {
    render(<ProfilePage />);
    const linkElement = screen.getByText('bio:');
    expect(linkElement).toBeInTheDocument();
  }) 
  
  test('Render ProfilePage', () => {
    render(<ProfilePage />);
    const genderElement = screen.getByText('gender');
    expect(genderElement).toBeInTheDocument();
  }) 

})