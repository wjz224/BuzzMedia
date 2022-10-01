import React from 'react';
import { cleanup, getByTestId, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import EditMsg from './EditMsg'

afterEach(() => { 
  cleanup();
})

describe("EditMsg Component" , () => {

  test('Render update message', () => {
    render(<EditMsg />);
    const linkElement = screen.getByText('Update Message:');
    expect(linkElement).toBeInTheDocument();
  }) 
})