import React from 'react';
import { cleanup, getByTestId, render, screen } from '@testing-library/react';
import App from './App';
import '@testing-library/jest-dom';

afterEach(() => { 
  cleanup();
})

describe("App: component rendering" , () => {

  test('Render HomePage', () => {
    render(<App />);
    const linkElement = screen.getByText('TheBuzz: Home page');
    expect(linkElement).toBeInTheDocument();
  }) 

  test('Render text for add message', () => {
    render(<App />);
    const linkElement = screen.getByText('Add Message:');
    expect(linkElement).toBeInTheDocument();
    
  })

  test('Render update message', () => {
    render(<App />);
    const linkElement = screen.getByText('Update Message:');
    expect(linkElement).toBeInTheDocument();
  })
   
})
