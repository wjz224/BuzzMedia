import { cleanup, render, screen } from '@testing-library/react';
import App from './App';
import '@testing-library/jest-dom';

//Testing file for App.tsx 


//Clears buffer after each test
afterEach(() => { 
  cleanup();
})

//3 tests included in the below description
describe("App: component rendering" , () => {

  //Test 1: looking for the text "TheBuzz: Home Page"
  test('Render HomePage', () => {
    render(<App />);
    const linkElement = screen.getByText('TheBuzz: Home page');
    expect(linkElement).toBeInTheDocument();
  }) 

  //Test 2: looking for the text "Add Message"
  test('Render text for add message', () => {
    render(<App />);
    const linkElement = screen.getByText('Add Message:');
    expect(linkElement).toBeInTheDocument();
    
  })

  //Test 3: looking for text "Update Message"
  test('Render update message', () => {
    render(<App />);
    const linkElement = screen.getByText('Update Message:');
    expect(linkElement).toBeInTheDocument();
  })
   
})
